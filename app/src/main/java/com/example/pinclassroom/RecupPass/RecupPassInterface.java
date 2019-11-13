package com.example.pinclassroom.RecupPass;

public interface RecupPassInterface {
    interface View {
        void disableInputs();
        void enableInputs();

        void showProgress();
        void hideProgress();

        void handleRecupPass();

        boolean isValidEmailLRecupPass();

        void onLogin();
        void onError(String error);
    }

    interface Presenter {
        void onDestroy();

        void toRecupPass(String emailLogin);

    }

    interface Model {
        void doRecupPass(String emailLogin);

    }

    interface TaskListener{
        void onSucess();
        void onError(String error);

    }
}
