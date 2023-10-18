import { useForm } from "react-hook-form";
import { Link, useLocation } from 'react-router-dom';
import styles from "./component.module.scss";
import { useEffect } from "react";
import trimObjectValues from "../../utils/strings/trimmedObj";
import experienceImage from "../../assets/images/Rectangle20.png";
import PropTypes from 'prop-types';
import ResendEmailForm from "./ResendEmail/ResendEmailForm";

const AuthForm = ({ submitData, errorMsg }) => {
  const { register,
    unregister,
    handleSubmit,
    reset,
    resetField,
    formState: {
      errors,
      isSubmitSuccessful,
      isSubmitting
    }
  } = useForm();

  const { pathname } = useLocation()

  const { message, status } = errorMsg

  const onSubmit = (data) => {
    const dataParsedToSend = trimObjectValues(data)
    submitData(dataParsedToSend)
  }

  const handleFieldClick = (fieldName) => {
    resetField(`${fieldName}`);
  };


  const handleResendEmail = (data1) => {

    console.log("Correo reenviado: ", data1);

    console.log(data1)
  };

  useEffect(() => {
    reset()
    if (pathname === '/login') {
      unregister("name")
      unregister("phone")
    }
  }, [isSubmitSuccessful, pathname, reset, unregister])

  return (
    <section className={styles.addExperienceSection}>
      <img src={experienceImage} alt="Digital_Escapes_Experience" className={styles.experienceImage} />
      <div  className={styles.formContainer}>
        <form onSubmit={handleSubmit(onSubmit)}>

          {pathname === '/login' && <div className={styles.fields}>
            <label className={styles.signUp}>Iniciar Sesión</label>
            <label className={styles.signUpDescrip}>Accede a tu cuenta en Digital Escapes.</label>
          </div>}

          {pathname === '/signup' && <div className={styles.fields}>
            <label className={styles.signUp}>Registro</label>
            <label className={styles.signUpDescrip}>Vamos a prepararte para que puedas acceder a tu cuenta personal.</label>
            <label>Nombre</label>
            <input
              {...register("name", { required: true, minLength: 3, maxLength: 40, pattern: /\S/g })}
              onClick={() => handleFieldClick("name")}
            />
            {errors.name?.type === 'required' && <span className={styles.errorFlag}>El campo es requerido</span>}
            {errors.name?.type === 'maxLength' && <span className={styles.errorFlag}>Supera el limite permitido de caracteres</span>}
            {errors.name?.type === 'minLength' && <span className={styles.errorFlag}>Ingresa como minimo 3 caracteres</span>}
            {errors.name?.type === 'pattern' && <span className={styles.errorFlag}>El campo no puede estar en blanco</span>}
          </div>}



          {pathname === '/signup' && <div className={styles.fields}>
            <label>Telefono</label>
            <input
              type="number"
              {...register("phone", { required: true, valueAsNumber: true, minLength: 1, pattern: /\S/g })}
              onClick={() => handleFieldClick("phone")}
            />
            {errors.phone?.type === 'required' && <span className={styles.errorFlag}>El campo es requerido</span>}
            {errors.phone?.type === 'minLength' && <span className={styles.errorFlag}>Ingresa como minimo 1 caracter</span>}
            {errors.phone?.type === 'pattern' && <span className={styles.errorFlag}>El campo no puede estar en blanco</span>}
            {errors.phone?.type === 'valueAsNumber' && <span className={styles.errorFlag}>Solo se acepta valores numericos</span>}
          </div>}

          <div className={styles.fields}>
            <label>Email</label>
            <input
              {...register(`${pathname === '/login' ? 'username' : 'email'}`, { required: true, pattern: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/g })}
              onClick={() => handleFieldClick(`${pathname === '/login' ? 'username' : 'email'}`)}
            />
            {errors.email?.type === 'required' && <span className={styles.errorFlag}>El campo es requerido</span>}
            {errors.email?.type === 'pattern' && <span className={styles.errorFlag}>Ingresa un correo eletronico valido</span>}
          </div>

          <div className={styles.fields}>
            <label>Password</label>
            <input
              type="password"
              {...register("password", { required: true, minLength: 4, pattern: /\S/g })}
              onClick={() => handleFieldClick("password")}
            />
            {errors.password?.type === 'required' && <span className={styles.errorFlag}>El campo es requerido</span>}
            {errors.password?.type === 'minLength' && <span className={styles.errorFlag}>La contraseña debe tener minimo 4 caracteres</span>}
            {errors.password?.type === 'pattern' && <span className={styles.errorFlag}>Ingrese una contraseña valida</span>}
          </div>

          {pathname === '/signup' && <div className={styles.terms}>
            <input type="checkbox"></input>
            <span className={styles.termsSpan}>Acepto todos los
              <a href="#" className={styles.termsAndConditions}> Términos </a> y la <a href="#" className={styles.termsAndConditions}> Política de Privacidad </a>.
            </span>
          </div>}

          <div className={styles.buttonContainer}>
            <button className={styles.submitButton} disabled={isSubmitting} type="submit">
              {pathname === '/signup' ? "Crear cuenta" : "Iniciar sesión"}
            </button>

            <Link className={styles.link} to={`${pathname === '/signup' ? '/login' : '/signup'}`}>
              {`${pathname === '/signup'
                ? '¿Ya tienes una cuenta? Inicia Sesión'
                : '¿No tienes una cuenta? Crear cuenta'}`}
            </Link>
          </div>



          {message &&
            <div className={styles.message}>
              <p className={`${status === "error" && styles.error}`}>{message}</p>
            </div>
          }

        </form>
        <div className={styles.resendMail}>{pathname === '/login' && <ResendEmailForm submitData={handleResendEmail} />}</div>

      </div>

    </section>
  )
}

AuthForm.propTypes = {
  submitData: PropTypes.func,
  errorMsg: PropTypes.object,
  setMsg: PropTypes.func
}

export default AuthForm
