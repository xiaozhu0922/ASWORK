package my.framework.mvp.presenter;

import android.os.Handler;
import android.os.Looper;

import my.framework.mvp.model.IUser;
import my.framework.mvp.model.UserModel;
import my.framework.mvp.view.ILoginView;

/**
 * Created by Administrator on 2017/3/26.
 */
public class LoginPresenterCompl implements ILoginPresenter {
    ILoginView iLoginView;
    IUser user;
    Handler handler;

    public LoginPresenterCompl(ILoginView iLoginView) {

        this.iLoginView = iLoginView;
        initUser();
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void clear() {
        iLoginView.onClearText();
    }

    @Override
    public void doLogin(String name, String passwd) {

        Boolean isLoginSuccess = true;
        final int code = user.checkUserValidity(name, passwd);
        if (code != 0) isLoginSuccess = false;
        final Boolean result = isLoginSuccess;
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                iLoginView.onLoginResult(result, code);
            }
        }, 3000);


    }

    @Override
    public void setProgressBarVisiblity(int visiblity) {
        iLoginView.onSetProgressBarVisibility(visiblity);
    }


    private void initUser() {
        user = new UserModel("mvp", "mvp");
    }
}
