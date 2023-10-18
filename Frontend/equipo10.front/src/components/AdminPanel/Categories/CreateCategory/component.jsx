import { useEffect, useState } from "react";
import { useForm } from "react-hook-form";
import { trim } from "ramda";
import CategoriesRepository from '../../../../repositories/categories.repository';
import styles from "./component.module.scss";
import { useNavigate } from "react-router-dom";

const CreateCategory = () => {

  const { register,
    handleSubmit,
    reset,
    formState: {
      errors,
      isSubmitSuccessful,
      isSubmitting
    }
  } = useForm();

  const [saveSuccesfully, setSaveSuccesfully] = useState(false);
  const navigate = useNavigate();

  const onSubmit = (data) => {

    const dataParsedToSend = {
      ...data,
      name: trim(data.name),
      description: trim(data.description),
    }
    const fetchData = async () => {
      try {
        await CategoriesRepository.addCategory(dataParsedToSend);
        setSaveSuccesfully(true)
      } catch (error) {
        console.log(error)
      }
    };
    fetchData();
  }

  const goBack = () => {
    navigate(-1);
  };

  useEffect(() => {
    if (isSubmitSuccessful) {
      reset();
    }

    setTimeout(() => {
      setSaveSuccesfully(false)
    }, 3000);

  }, [isSubmitSuccessful, reset])

  return (
    <section className={styles.addCategorySection}>
      <h1>Agregar Categoría</h1>
      <button className={styles.goBackButton} onClick={goBack}>
        <i className="las la-angle-left"></i>
        Volver
      </button>
      <form className={styles.formContainer} onSubmit={handleSubmit(onSubmit)}>
        <div className={styles.fields}>
          <label>Título</label>
          <input {...register("name", { required: true, minLength: 3, maxLength: 40, pattern: /\S/g })} />
          {errors.name?.type === 'required' && <span className={styles.errorFlag}>El campo es requerido</span>}
          {errors.name?.type === 'maxLength' && <span className={styles.errorFlag}>Supera el limite permitido de caracteres</span>}
          {errors.name?.type === 'minLength' && <span className={styles.errorFlag}>Ingresa como minimo 3 caracteres</span>}
          {errors.name?.type === 'pattern' && <span className={styles.errorFlag}>El campo no puede estar en blanco</span>}
        </div>

        <div className={styles.fields}>
          <label>Descripcion</label>
          <textarea rows="5" {...register("description", { required: true, minLength: 3, maxLength: 255, pattern: /\S/g })} />
          {errors.description?.type === 'required' && <span className={styles.errorFlag}>El campo es requerido</span>}
          {errors.description?.type === 'maxLength' && <span className={styles.errorFlag}>Supera el limite permitido de caracteres</span>}
          {errors.description?.type === 'minLength' && <span className={styles.errorFlag}>Ingresa como minimo 3 caracteres</span>}
          {errors.description?.type === 'pattern' && <span className={styles.errorFlag}>El campo no puede estar en blanco</span>}
        </div>

        <div className={styles.fields}>
          <label>URL de imagen</label>
          <input {...register("imgUrl", { required: true })} />
          {errors.imgUrl?.type === 'required' && <span className={styles.errorFlag}>El campo es requerido</span>}
        </div>

        <div className={styles.buttonContainer}>
          <button
            className={styles.submitButton}
            disabled={isSubmitting}
            type="submit"
          >
            Crear Categoría
          </button>
        </div>

        {saveSuccesfully &&
          <div className={styles.message}>
            <p>La categoría fue agregada con éxito!</p>
          </div>}
      </form>
    </section>

  )

}

export default CreateCategory;