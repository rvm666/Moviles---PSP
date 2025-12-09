package org.example.restspring.ui.config;

public class Constantes {

    private Constantes() {}

    public static final String USUARIO = "usuario";
    public static final String REST_PRODUCCIONES = "/rest/producciones";
    public static final String REST_USUARIOS = "/rest/usuarios";
    public static final String REST_ACTIVACION = "/rest/activar";




    public static final String PREFIJO = "templates/";
    public static final String SUFIJO = ".html";
    public static final String ENCODING = "UTF-8";



    public static final String USUARIO_NO_ENCONTRADO = "Usuario no encontrado";
    public static final String CODIGO_INVALIDO = "No se ha podido activar su cuenta. CODIGO INVALIDO";
    public static final String CREDENCIALES_INVALIDAS = "Las credenciales son invalidas";
    public static final String CODIGO = "codigo";
    public static final String ACTIVACION_CUENTA = "Activación de la cuenta";
    public static final String ENLACE = "enlaceActivacion";
    public static final String EMAIL = "email";
    public static final String ERROR_AL_ENVIAR_CORREO1 = "Error al enviar correo a {}";
    public static final String ERROR_AL_ENVIAR_CORREO2 = "Error al enviar el correo de activación.";
    public static final String PRODUCCION_CON_ID = "Produccion con id: ";
    public static final String NO_ENCONTRADA = " no encontrada";
    public static final String USUARIO_CON_ID = "El usuario con id: ";
    public static final String NO_TIENE_PRODUCCIONES = " no tiene producciones o no existe";
    public static final String NO_SE_HA_ENCONTRAD_NOMBRE = "No se ha encontrado la produccion con el nombre: ";
    public static final String NO_SE_HA_PODIDO_ELIMINAR_PRODUCCION = "No se ha podido eliminar la produccion con id: ";
    public static final String PUEDE_QUE_NO_EXISTA = ", puede que no exista o no pertenezca al usuario con id: ";
    public static final String TIENE_QUE_INICIAR_SESION = "Tiene que iniciar sesion";
    public static final String NO_TIENE_PERMISOS = "No tiene permisos para acceder";
    public static final String EL_USUARIO = "El usuario ";
    public static final String YA_ESTA_REGISTRADO = " ya está registrado";


    public static final String BASE_URL = "${app.path.base-url}";
    public static final String ACTIVAR_URL = "${app.path.activar}";
    public static final String LOGIN_URL = "${app.path.login}";
    public static final String REGISTRAR_URL = "${app.path.registro}";
    public static final String INTERROGACION_CODIGO = "?codigo=";
    public static final String PATH_PATTERNS = "/rest/**";

}
