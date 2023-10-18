import { useState } from "react";
import PropTypes from "prop-types";
import { useForm } from "react-hook-form";
import trimObjectValues from "../../../utils/strings/trimmedObj";
import AuthRepository from "../../../repositories/auth.repository";
import styles from "./component.module.scss";

const ResendEmailForm = ({ submitData }) => {
  const {
    register,
    handleSubmit,
    formState: { errors, isSubmitting },
    setError,
  } = useForm();

  const [isFormOpen, setIsFormOpen] = useState(false);
  const [errorMessage, setErrorMessage] = useState(null);

  const onSubmit = async (data) => {
    const dataParsedToSend = trimObjectValues(data);
    setErrorMessage("")
    try {
      const response = await AuthRepository.resendEmail(dataParsedToSend.newEmail);
      setIsFormOpen(false);
      submitData(response); 
      setErrorMessage("")
    } catch (error) {
      if (error.response && error.response.data) {
        setErrorMessage(error.response.data);
      } else {
        setErrorMessage("Hubo un error en la solicitud de reenvío de correo electrónico.");
      }
      setError("newEmail", { message: errorMessage });
    }
  };

  const openResendForm = () => {
    setIsFormOpen(true);
    setErrorMessage(null);
  };

  const closeResendForm = () => {
    setIsFormOpen(false);
    setErrorMessage(null);
  };

  return (
    <>
      <button className={styles.resendButton} onClick={openResendForm} type="button">
        ¿No recibiste el correo de confirmación? Reenviar
      </button>

      {isFormOpen && (
        <div className={styles.resendFormOverlay}>
          <div className={styles.resendFormContainer}>
            <div className={styles.closeButtonContainer}>
              <button className={styles.closeButton} onClick={closeResendForm} type="button">
                X
              </button>
            </div>
            <form className={styles.resendForm} onSubmit={handleSubmit(onSubmit)}>
              <label>Ingresa tu correo</label>
              <input
                {...register("newEmail", {
                  required: "El campo es requerido",
                  pattern: {
                    value: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/g,
                    message: "Ingresa un correo electrónico válido",
                  },
                })}
                name="newEmail"
              />
              {errors.newEmail && (
                <span className={styles.errorFlag}>{errors.newEmail.message}</span>
              )}

              {/* {errorMessage && (
                <span className={styles.errorFlag}>{errorMessage}</span>
              )} */}

              <button className={styles.submitButton} disabled={isSubmitting} type="submit">
                Reenviar
              </button>
            </form>
          </div>
        </div>
      )}
    </>
  );
};

ResendEmailForm.propTypes = {
  submitData: PropTypes.func.isRequired,
};

export default ResendEmailForm;
