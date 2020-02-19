package com.example.mvvmapplication.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mvvmapplication.data.LoginRepository;
import com.example.mvvmapplication.data.Result;
import com.example.mvvmapplication.data.model.LoggedInUser;
import com.example.mvvmapplication.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginResult> loginResult;
    private MutableLiveData<Boolean> logging;
    //注意！在xml文件中关联livedate对象时候需要调用get方法，需要显式写出，否则xml文件中无法识别
    public MutableLiveData<Boolean> getLogging() {
        if (logging == null) {
            return new MutableLiveData<>();
        }
        return logging;
    }

    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }


    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login() {
        // can be launched in a separate asynchronous job
        Result<LoggedInUser> result = loginRepository.login();

        if (result instanceof Result.Success) {
            LoggedInUser data = ((Result.Success<LoggedInUser>) result).getData();
            loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
        } else {
            loginResult.setValue(new LoginResult(R.string.login_failed));
        }
    }

    public void loginByQQ() {
        logging.setValue(true);
        loginRepository.loginByQQ();
    }

    public void loginByWX() {
        logging.setValue(true);
        loginRepository.loginByWX();
    }

    public void loginOneKey() {
        logging.setValue(true);
        loginRepository.loginOneKey();
    }

    private void logging() {

    }

}
