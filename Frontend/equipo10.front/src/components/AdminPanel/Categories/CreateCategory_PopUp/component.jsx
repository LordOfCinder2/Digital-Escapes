import { useState } from "react";
import { trim } from "ramda";
import CategoriesRepository from '../../../../repositories/categories.repository';
import styles from "./component.module.scss";
import PropTypes from 'prop-types';

const CreateCategoryPopUp = (props) => {

  const [saveSuccesfully, setSaveSuccesfully] = useState(false);
  const [nombre, setNombre] = useState('');
  const [descripcion, setDescripcion] = useState('');
  const [imagen, setImagen] = useState('');

  const handleChangeNombre = (e) => {
    setNombre(e.target.value);
  }

  const handleChangeDescripcion = (e) => {
    setDescripcion(e.target.value);
  }

  const handleChangeImage = (e) => {
    setImagen(e.target.value);
  }

  const uploadData = (nam, descr, imag) => {

    if (nam != '' && descr != '' && imag != '') {

      const dataParsedToSend = {
        name: trim(nam),
        description: trim(descr),
        imgUrl: imag
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
  }

  return (props.trigger) ? (
    <div className={styles.popup}>
      <div className={styles['popup-inner']}>
        <section className={styles.addCategorySection}>
          <h1>Agregar Categoría</h1>
          <div className={styles.fields}>
            <h2>Título</h2>
            <input value={nombre} onChange={handleChangeNombre}></input>
          </div>

          <div className={styles.fields}>
            <h2>Descripción</h2>
            <textarea name="descripcion" cols="40" rows="4" value={descripcion} onChange={handleChangeDescripcion}></textarea>
          </div>

          <div className={styles.fields}>
            <h2>URL de imagen</h2>
            <input value={imagen} onChange={handleChangeImage}></input>
          </div>

          <div className={styles.buttonContainer}>
            <button
              className={styles.submitButton}
              onClick={() => { uploadData(nombre, descripcion, imagen) }}
              type="button"
            >
              Crear Categoría
            </button>
          </div>

          {saveSuccesfully &&
            <div className={styles.message}>
              <p>La categoría fue agregada con éxito!</p>
            </div>}
        </section>

        <button
          className={styles['close-btn']}
          onClick={() => props.setTrigger(false)}
        >X</button>
      </div>
    </div>
  ) : "";
}

CreateCategoryPopUp.propTypes = {
  trigger: PropTypes.bool,
  setTrigger: PropTypes.func
}

export default CreateCategoryPopUp