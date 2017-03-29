package my.framework.mvp.presenter;

/**
 * Created by Administrator on 2017/3/26.
 */
public interface ILoginPresenter {

    void clear();

    void doLogin(String name, String passwd);

    void setProgressBarVisiblity(int visiblity);
}
