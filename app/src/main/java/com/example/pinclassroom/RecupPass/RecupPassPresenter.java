package com.example.pinclassroom.RecupPass;

public class RecupPassPresenter implements RecupPassInterface.Presenter, RecupPassInterface.TaskListener{

    private RecupPassInterface.View view;
    private RecupPassInterface.Model model;

    public RecupPassPresenter(RecupPassInterface.View view){
        this.view = view;
        model = new RecupPassModel(this);

    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void toRecupPass(String emailLogin) {
        if(view!=null){
            view.disableInputs();
            view.showProgress();
        }
        model.doRecupPass(emailLogin);
    }

    @Override
    public void onSucess() {
        if (view!=null){
            view.enableInputs();
            view.hideProgress();
            view.onLogin();
        }
    }

    @Override
    public void onError(String error) {
        if (view!=null){
            view.enableInputs();
            view.onError(error);
        }
    }
}
