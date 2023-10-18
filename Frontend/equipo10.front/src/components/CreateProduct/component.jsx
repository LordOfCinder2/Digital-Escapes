import { useEffect, useRef, useState } from "react";
import { useForm } from "react-hook-form";
import ExperiencesRepository from '../../repositories/experiences.repository';
import Popup from '../CreateCategory_PopUp/component';
import { isNotEmpty, toNumber } from "ramda-adjunct";
import { trim } from "ramda";
import experienceTitleToSlug from "../../utils/strings/experienceTitleToSlug";
import styles from "./component.module.scss";

const CreateProduct = () => {
  const { register,
    handleSubmit,
    reset,
    formState: {
      errors,
      isSubmitSuccessful,
      isSubmitting
    }
  } = useForm();

  const [categories, setCategories] = useState([]);
  const [saveSuccesfully, setSaveSuccesfully] = useState(false);
  const [buttonPopup, setButtonPopup] = useState(false);

  const apiCalled = useRef(false);

  const onSubmit = (data, event) => {
    event.preventDefault();
    const dataParsedToSend = {
      ...data,
      name: trim(data.name),
      description: trim(data.description),
      location: trim(data.location),
      category: {
        id: toNumber(data.category)
      },
      experienceSlug: experienceTitleToSlug(data.name)
    }
    console.log(dataParsedToSend)

    const fetchData = async () => {
      try {
        const response = await ExperiencesRepository.addExperience(dataParsedToSend);
        setSaveSuccesfully(true)
        console.log(response)
      } catch (error) {
        console.log(error)
      }
    };
    fetchData();
  }

  useEffect(() => {
    if (isSubmitSuccessful) {
      reset();
    }
    setTimeout(() => {
      setSaveSuccesfully(false)
    }, 3000);
    if (!apiCalled.current) {
      const fetchData = async () => {
        try {
          const response = await ExperiencesRepository.searchCategories();
          setCategories(response)
        } catch (error) {
          console.log(error)
        }
      };
      fetchData();
      apiCalled.current = true;
    }
  }, [isSubmitSuccessful, reset])

  return (
    <section className={styles.addExperienceSection}>
      <h1>Agregar Experiencia</h1>
      <form className={styles.formContainer} onSubmit={handleSubmit(onSubmit)}>
        <div className={styles.fields}>
          <label>Titulo</label>
          <input {...register("name", { required: true, minLength: 3, maxLength: 40, pattern: /\S/g })} />
          {errors.name?.type === 'required' && <span className={styles.errorFlag}>El campo es requerido</span>}
          {errors.name?.type === 'maxLength' && <span className={styles.errorFlag}>Supera el limite permitido de caracteres</span>}
          {errors.name?.type === 'minLength' && <span className={styles.errorFlag}>Ingresa como minimo 3 caracteres</span>}
          {errors.name?.type === 'pattern' && <span className={styles.errorFlag}>El campo no puede estar en blanco</span>}
        </div>

        <div className={styles.fields}>
          <label>Descripcion</label>
          <textarea rows="3" {...register("description", { required: true, minLength: 3, maxLength: 255, pattern: /\S/g })} />
          {errors.description?.type === 'required' && <span className={styles.errorFlag}>El campo es requerido</span>}
          {errors.description?.type === 'maxLength' && <span className={styles.errorFlag}>Supera el limite permitido de caracteres</span>}
          {errors.description?.type === 'minLength' && <span className={styles.errorFlag}>Ingresa como minimo 3 caracteres</span>}
          {errors.description?.type === 'pattern' && <span className={styles.errorFlag}>El campo no puede estar en blanco</span>}
        </div>

        <div className={styles.fields}>
          <label>Url de imagen</label>
          <input {...register("imgUrl", { required: true })} />
          {errors.imgUrl?.type === 'required' && <span className={styles.errorFlag}>El campo es requerido</span>}
        </div>

        <div className={styles.fields}>
          <label>Precio</label>
          <input type="number" {...register("price", { required: true, valueAsNumber: true, minLength: 1, pattern: /\S/g })} />
          {errors.price?.type === 'required' && <span className={styles.errorFlag}>El campo es requerido</span>}
          {errors.price?.type === 'valueAsNumber' && <span className={styles.errorFlag}>Solo se acepta valores numericos</span>}
          {errors.price?.type === 'minLength' && <span className={styles.errorFlag}>Ingresa como minimo 1 digito </span>}
          {errors.price?.type === 'pattern' && <span className={styles.errorFlag}>El campo no puede estar en blanco</span>}
        </div>

        <div className={styles.fields}>
          <label>Locacion</label>
          <input {...register("location", { required: true, minLength: 3, pattern: /\S/g })} />
          {errors.location?.type === 'required' && <span className={styles.errorFlag}>El campo es requerido</span>}
          {errors.location?.type === 'minLength' && <span className={styles.errorFlag}>Ingresa como minimo 3 caracteres</span>}
          {errors.location?.type === 'pattern' && <span className={styles.errorFlag}>El campo no puede estar en blanco</span>}
        </div>

        <div className={styles.fields}>
          <label>Categoria</label>
          <select {...register("category", { required: true })}>
            {isNotEmpty(categories)
              && categories.map((element, index) => (
                <option key={index} value={element.id}>{element.name}</option>
              ))}
          </select>
          {errors.category?.type === 'required' && <span className={styles.errorFlag}>El campo es requerido</span>}
          <button
            className={styles.addCategoryButton}
            onClick={() => setButtonPopup(true)}
            type="button"
          >+ Agregar Categoría
          </button>
          {buttonPopup && <Popup trigger={buttonPopup} setTrigger={setButtonPopup} />}
        </div>

        <div className={styles.buttonContainer}>
          <button
            className={styles.submitButton}
            disabled={isSubmitting}
            type="submit"
          >
            Crear Producto
          </button>
        </div>
        {saveSuccesfully &&
          <div className={styles.message}>
            <p>El producto fue agregado con éxito!</p>
          </div>}
      </form>
    </section>
  );
}

export default CreateProduct;