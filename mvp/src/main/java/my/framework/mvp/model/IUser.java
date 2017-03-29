package my.framework.mvp.model;

/**
 * Created by Administrator on 2017/3/26.
 */
public interface IUser {

    String getName();

    String getPasswd();

    int checkUserValidity(String name, String passwd);

}
