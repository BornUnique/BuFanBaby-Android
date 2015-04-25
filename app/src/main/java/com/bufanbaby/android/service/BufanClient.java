package com.bufanbaby.android.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.bufanbaby.android.exception.DataConversionException;
import com.bufanbaby.android.exception.ServerConnectionException;
import com.bufanbaby.android.exception.ServerResponseException;
import com.bufanbaby.android.model.CommonConsts;
import com.bufanbaby.android.model.Deed;
import com.bufanbaby.android.util.JsonUtils;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntityHC4;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Provide Intent Service to BunfanBaby Service {@link IntentService}
 */
public class BufanClient extends IntentService {
	private static final Logger log = LoggerFactory.getLogger(BufanClient.class);
	private static final String INTENT_REPLY_TO = "orgin.activity";

	private final CloseableHttpClient http = HttpClients.createDefault();

	public static interface ClientFunc {
		String NS = "com.bufanbaby.android.service";

		enum ActionName {
			LOGIN(".action.login"),
			LOGOUT(".action.logout"),
			POST(".action.post"),
			SEARCH(".action.search"),
			HIDE(".action.hide");

			private ActionName(String actionId) {
				this.id = NS + actionId;
				getNameMap().put(id, this);
			}

			public static ActionName from(String str) {
				return nameToEnum.get(str);
			}

			public static Map<String, ActionName> getNameMap() {
				if(null == nameToEnum) {
					nameToEnum = new HashMap<String, ActionName>();
				}
				return nameToEnum;
			}

			private static Map<String, ActionName> nameToEnum;
			public final String id;
		}


		interface ParamName {
			String uid = ".param.uid";
			String pid = ".param.pid";
			String post = ".param.post";
			String image = ".param.image";
			String subject = ".param.subject";
			String criteria = ".param.criteria";

			String user = ".param.user";
			String password = ".param.password";
			String token = ".param.token";
		}
	}

	public static void doPost(Context ctx, Deed post) {
		Intent i = new Intent(ctx, BufanClient.class);
		i.setAction(ClientFunc.ActionName.POST.id)
			.putExtra(INTENT_REPLY_TO, ctx.getClass().getCanonicalName())
			.putExtra(ClientFunc.ParamName.post, post);
		ctx.startService(i);
	}

	/**
	 * Run a query to server
	 */
	public static void doSearch(Context ctx, String subject, String criteria) {
		Intent i = new Intent(ctx, BufanClient.class);
		i.setAction(ClientFunc.ActionName.SEARCH.id)
			.putExtra(INTENT_REPLY_TO, ctx.getClass().getCanonicalName())
			.putExtra(ClientFunc.ParamName.subject, subject)
			.putExtra(ClientFunc.ParamName.criteria, criteria);
		ctx.startService(i);
	}

	public static void doLogin(Context ctx, String user, String passwd) {
		Intent i = new Intent(ctx, BufanClient.class);
		i.setAction(ClientFunc.ActionName.LOGIN.id)
			.putExtra(INTENT_REPLY_TO, ctx.getClass().getCanonicalName())
			.putExtra(ClientFunc.ParamName.user, user)
			.putExtra(ClientFunc.ParamName.password, passwd);
		ctx.startService(i);
	}

	public BufanClient() {
		super("BufanClient");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		if (intent != null) {
			final String actionStr = intent.getAction();
			Intent resp = null;
			ClientFunc.ActionName action = ClientFunc.ActionName.from(actionStr);
			if(null != action) {
				switch(action) {
				case POST: {
					Deed deed = (Deed) intent.getSerializableExtra(ClientFunc.ParamName.post);
					resp = handlePost(deed);
					break;
				}
				case SEARCH: {
					String subj = intent.getStringExtra(ClientFunc.ParamName.subject);
					String crit = intent.getStringExtra(ClientFunc.ParamName.criteria);
					resp = handleSearch(subj, crit);
					break;
				}
				case LOGIN: {
					String user = intent.getStringExtra(ClientFunc.ParamName.user);
					String passwd = intent.getStringExtra(ClientFunc.ParamName.password);
					resp = handleLogin(user, passwd);
					break;
				}
				}
			}
			if(null != resp) {
				String replyTo = intent.getStringExtra(INTENT_REPLY_TO);
				resp.setAction(replyTo);
				this.sendBroadcast(resp);
			}
		}
	}

	private Intent handleLogin(String user, String passwd) {
		throw new UnsupportedOperationException();
	}

	private Intent handlePost(Deed deed) {
		String content;
		try {
			content = JsonUtils.toJson(deed);
		} catch (DataConversionException e) {
			Intent ierr = new Intent();
			ierr.putExtra(CommonConsts.IntentResultTypeName, CommonConsts.IntentResultType.CLIENT_INPUT_ERROR.ordinal());
			return ierr;
		}

		HttpPostHC4 post = new HttpPostHC4();
		StringEntityHC4 entity = new StringEntityHC4(content, ContentType.APPLICATION_JSON);
		post.setEntity(entity);
		try {
			String resp = performServerCall(post);
			Intent iok = new Intent();
			iok.putExtra(CommonConsts.IntentResultTypeName, CommonConsts.IntentResultType.OK.ordinal());
			iok.putExtra(CommonConsts.IntentResult, resp);
			return iok;
		} catch (ServerResponseException e) {
			Intent ierr = new Intent();
			ierr.putExtra(CommonConsts.IntentResultTypeName, CommonConsts.IntentResultType.SERVER_GENERIC_ERROR.ordinal());
			return ierr;
		}
	}

	private Intent handleSearch(String subj, String crit) {
		throw new UnsupportedOperationException();
	}

	protected String performServerCall(HttpUriRequest req) throws ServerResponseException {
		//return httpServerCall();

		// TODO: move test code to UnitTest
		return "{ \"content\": \"mock result\"}";
	}

	private String httpServerCall(HttpUriRequest req) throws ServerResponseException {
		CloseableHttpResponse resp = null;
		try {
			resp = http.execute(req);
			if(resp.getStatusLine().getStatusCode() >= 400) {
				ServerResponseException ex = new ServerResponseException();
				ex.setHttpErrorCode(resp.getStatusLine().getStatusCode());
				ex.setHttpErrorDetail(resp.getStatusLine().getReasonPhrase());
				throw ex;
			}
			// Entity is the whole response body. It could be json (assuming server always return
			// json). It is advisable we use a json structure wrapper for the server response to
			// include restful response such as 201 Created, 204 Empty Body, etc
			HttpEntity entity = resp.getEntity();
			InputStream is = entity.getContent();
			String content = IOUtils.toString(is);
			return content;
		} catch (IOException e) {
			ServerConnectionException ex = new ServerConnectionException();
			throw ex;
		} finally {
			if(null != resp) {
				try {
					resp.close();
				} catch (IOException e) {
					log.info("Failed to close http response: {}", e);
				}
			}
		}

	}
}
