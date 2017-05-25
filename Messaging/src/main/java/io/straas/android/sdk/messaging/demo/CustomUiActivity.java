package io.straas.android.sdk.messaging.demo;


import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import java.util.Random;

import io.straas.android.sdk.demo.R;
import io.straas.android.sdk.messaging.ChatMode;
import io.straas.android.sdk.messaging.ChatroomManager;
import io.straas.android.sdk.messaging.ChatroomState;
import io.straas.android.sdk.messaging.Message;
import io.straas.android.sdk.messaging.User;
import io.straas.android.sdk.messaging.interfaces.EventListener;
import io.straas.android.sdk.messaging.ui.ChatroomOutputView;
import io.straas.android.sdk.messaging.ui.interfaces.CredentialAuthorizeListener;
import io.straas.sdk.demo.MemberIdentity;
import tyrantgit.widget.HeartLayout;

public class CustomUiActivity extends AppCompatActivity {

    private static final String CHATROOM_NAME = "test_chatroom";
    private ChatroomOutputView mChatroomOutputView;

    private static final String LIKE = "like";
    private static final String LOVE = "love";
    private static final String XD = "XD";

    private HeartLayout mHeartLayout;
    private ViewGroup mInputBar;
    private EditText mInput;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_ui);

        mHeartLayout = (HeartLayout) findViewById(R.id.heart);
        mInputBar = (ViewGroup) findViewById(R.id.inputBar);
        mInput = (EditText) findViewById(android.R.id.edit);
        mChatroomOutputView = (ChatroomOutputView) findViewById(R.id.chat_room);
        mChatroomOutputView.setCredentialAuthorizeListener(mCredentialAuthorizeListener);
        mChatroomOutputView.setEventListener(mEventListener);
        mChatroomOutputView.setMessageItemCustomView(R.layout.custom_message_item);
        mChatroomOutputView.connect(CHATROOM_NAME, MemberIdentity.ME, false);
    }

    private CredentialAuthorizeListener mCredentialAuthorizeListener =
        new CredentialAuthorizeListener() {
            @Override
            public void onSuccess(ChatroomManager chatRoomManager) {

            }

            @Override
            public void onFailure(Exception error) {

            }
    };

    public void showKeyboard(View view) {
        mInputBar.setVisibility(View.VISIBLE);
        mInput.requestFocus();
        showKeyboard(CustomUiActivity.this, mInput, InputMethodManager.SHOW_FORCED);
    }

    public static void showKeyboard(Activity activity, View view, int flag) {
        InputMethodManager inputManager = (InputMethodManager)activity.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(view, flag);
    }


    public void love(View view) {
        ChatroomManager manager = mChatroomOutputView.getChatroomManager();
        if (manager.getChatroomState() == ChatroomState.CONNECTED) {
            int i = new Random().nextInt(3);
            switch (i) {
                case 0:
                    manager.sendAggregatedData(XD);
                    break;
                case 1:
                    manager.sendAggregatedData(LOVE);
                    break;
                case 2:
                    manager.sendAggregatedData(LIKE);
                    break;
            }
        }
    }

    private EventListener mEventListener = new EventListener() {
        @Override
        public void onConnectFailed(Exception error) {

        }


        @Override
        public void onError(Exception error) {

        }

        @Override
        public void onAggregatedDataAdded(SimpleArrayMap<String, Integer> map) {
            if (map.containsKey(XD)) {
                startAnimation(R.drawable.ic_emoji_xd, map.get(XD));
            }
            if (map.containsKey(LIKE)) {
                startAnimation(R.drawable.ic_emoji_like, map.get(LIKE));
            }
            if (map.containsKey(LOVE)) {
                startAnimation(R.drawable.ic_emoji_heart, map.get(LOVE));
            }
        }

        private void startAnimation(@DrawableRes int id, int count) {
            int i = 0;
            while (i < count) {
                ImageView imageView;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    imageView = new ImageView(CustomUiActivity.this);
                } else {
                    imageView = new AppCompatImageView(CustomUiActivity.this);
                }
                imageView.setImageResource(id);
                mHeartLayout.getAnimator().start(imageView, mHeartLayout);
                i++;
            }
        }


        @Override
        public void onConnected() {

        }

        @Override
        public void onRawDataAdded(Message message) {

        }

        @Override
        public void onDisconnected() {

        }

        @Override
        public void onChatWriteModeChanged(ChatMode chatMode) {

        }

        @Override
        public void onInputIntervalChanged(int interval) {

        }

        @Override
        public void onMessageAdded(Message message) {

        }

        @Override
        public void onMessageRemoved(String messageId) {

        }

        @Override
        public void onMessageFlushed() {

        }

        @Override
        public void onUserJoined(User[] users) {

        }

        @Override
        public void onUserUpdated(User[] users) {

        }

        @Override
        public void onUserLeft(Integer[] userLabels) {

        }

        @Override
        public void userCount(int userCount) {

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mChatroomOutputView.disconnect();
    }
}

