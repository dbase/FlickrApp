package com.hrupin.flickrapp.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.os.AsyncTask;

import com.gmail.yuyang226.flickr.Flickr;
import com.gmail.yuyang226.flickr.oauth.OAuth;
import com.gmail.yuyang226.flickr.oauth.OAuthInterface;
import com.hrupin.flickrapp.FlickrHelper;
import com.hrupin.flickrapp.activities.LoginActivity;

public class GetOAuthTokenTask extends AsyncTask<String, Integer, OAuth> {
    private static final Logger logger = LoggerFactory.getLogger(GetOAuthTokenTask.class);

    private LoginActivity activity;

    public GetOAuthTokenTask(LoginActivity context) {
        this.activity = context;
    }

    @Override
    protected OAuth doInBackground(String... params) {
        String oauthToken = params[0];
        String oauthTokenSecret = params[1];
        String verifier = params[2];

        Flickr f = FlickrHelper.getInstance().getFlickr();
        OAuthInterface oauthApi = f.getOAuthInterface();
        try {
            return oauthApi.getAccessToken(oauthToken, oauthTokenSecret, verifier);
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);
            return null;
        }

    }

    @Override
    protected void onPostExecute(OAuth result) {
        if (activity != null) {
            activity.onOAuthDone(result);
        }
    }

}
