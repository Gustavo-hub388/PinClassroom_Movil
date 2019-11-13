package com.example.pinclassroom.Login;

public interface LoginInterface {
    /*Se declaran las 4 interfaces principales
    *View, Presenter, Model, Tasklistener
     */

    // Esto es todoo lo que va a controlar la vista
    interface View {
        //Metodos

        void disableInputs();//desactivar los componentes despues de dar inicio una accion
        void enableInputs();//activar los componentes despues terminar una accion

        void showProgress();//Mostrar un progreso mientras cuando envia los datos al presentador
        void hideProgress();//ocultar el progreso

        void handleLogin();//se encarga de que todoo vaya bien durante la accion

        boolean isValidEmailLogin();//Validar el correo
        boolean isValidPasswordLogin();//validar la contraseña

        void onLogin();
        void onError(String error);
    }

    // Se encargara de trabajar con la vista de todos lo que se muestre
    interface Presenter {
        //Metodos
        void onDestroy();//elimina la vista todoo los residuos innecesarios

        void toLogin(String emailLogin, String passwordLogin);//envia la modelo el correo y contraseña

    }

    /*se encargara de verificar si los datos dados por el presentador son validos o no
     * Y le da aviso al tasklistener
     */
    interface Model {
        //Metodo
        void doLogin (String emailLogin, String passwordLogin);//pasamos los datos que verificara

    }

    //Le da aviso al Presentador si son correctos los datos o no

    interface TaskListener{
        //Metodos
        void onSucess();//dice que si estuvo correcto todoo
        void onError(String error);//dice si huvo problemas

    }
}
