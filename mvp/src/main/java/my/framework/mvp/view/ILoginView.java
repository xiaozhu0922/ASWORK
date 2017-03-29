package my.framework.mvp.view;

/**
 * Created by Administrator on 2017/3/26.
 */
public interface ILoginView {

    public void onClearText();

    public void onLoginResult(Boolean result, int code);

    public void onSetProgressBarVisibility(int visibility);
}
