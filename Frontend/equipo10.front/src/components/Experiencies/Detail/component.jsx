import { Link, useParams } from 'react-router-dom';
import styles from "./component.module.scss";
import Spinner from "../../Spinner/component";
import { useContext, useEffect, useRef } from 'react';
import { ExperiencesContext } from '../../../context/coreData/experiences.context';
import ExperiencesRepository from '../../../repositories/experiences.repository';
import Popup from '../../Gallery_PopUp/component';
import PopupResena from '../../Resena_PopUp/component';
import { useState } from 'react';
import { FaStar } from "react-icons/fa";
import Characteristic from '../../CharacteristicBlock/component';
import ScoreRepository from '../../../repositories/score.repository';
import BookingForm from '../../Booking/component';

const Detail = () => {

  const { experienceSlug } = useParams()

  const { experienceData, updateExperienceData } = useContext(ExperiencesContext);
  const [loading, setLoading] = useState(true);
  const { selectedExperience, error } = experienceData;
  const [buttonPopup, setButtonPopup] = useState(false);
  const [resenaPopup, setResenaPopup] = useState(false);
  const [rating, setRating] = useState(0);
  const [saveSuccesfully, setSaveSuccesfully] = useState(false);
  const apiCalled = useRef(false);

  const uploadData = (score, idExp) => {

    const token = localStorage.getItem('token');

    const dataParsedToSend = {
      rating: score,
      experienceId: idExp
    }

    const fetchData = async () => {
      try {
        await ScoreRepository.addScore(dataParsedToSend, token);
        // const response = await ScoreRepository.getAverage(idExp, token);
        setSaveSuccesfully(true)
      } catch (error) {
        console.log(error)
      }
    };
    fetchData();
  }

  useEffect(() => {

    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    });

    if (!apiCalled.current) {
      const fetchData = async () => {
        try {
          const response = await ExperiencesRepository.getBySlug(experienceSlug);
          updateExperienceData(
            {
              selectedExperience: response
            }
          )
          setLoading(false)
        } catch (error) {
          updateExperienceData({ error: error })
          setLoading(false)
        }
      };
      fetchData();
      apiCalled.current = true
    }

  }, [experienceSlug, updateExperienceData])

  return (
    <section className={styles.detailSection}>
      {loading && <Spinner />}
      {error && <p>{error.message}</p>}
      {selectedExperience &&
        <>
          <div className={styles.navBackButton}>
            <h1 className={styles.title}>{selectedExperience.name}</h1>
            <Link to={'/home'} className={styles.backButton}>
              <i className="las la-chevron-left" />
              Volver
            </Link>
          </div>
          <div className={styles.mainDetailContainer}>
            <div className={styles.gridcontainer}>
              <img src={selectedExperience.imgUrl} className={styles.column}></img>
              <a href='#' className={styles.imgTotal}>1/5</a>
              <div className={styles.column}>
                <div className={styles.grid2x2}>
                  <img src={selectedExperience.imgUrl} className={styles.item}></img>
                  <img src={selectedExperience.imgUrl} className={styles.item}></img>
                  <img src={selectedExperience.imgUrl} className={styles.item}></img>
                  <div className={styles.item}>
                    <img src={selectedExperience.imgUrl} className={styles.lastItem}></img>
                    <a className={styles.overlay} onClick={() => setButtonPopup(true)}>Ver más</a>
                    {buttonPopup && <Popup trigger={buttonPopup} setTrigger={setButtonPopup} />}
                  </div>
                </div>
              </div>
            </div>
            <div className={styles.bookingForm}>
              <BookingForm  />
            </div>
          </div>

          <div className={styles.descriptionContainer}>
            <h2>{selectedExperience.name}</h2>
            <p>{selectedExperience.description}</p>
            <p>Locación: {selectedExperience.location}</p>
            <p>Precio:
              <span className={styles.price}>
                ${selectedExperience.price}
              </span>
            </p>
          </div>

          <div className={styles.ratingContainer}>
            <hr />
            <h2>¿Qué tal estuvo tu experiencia?</h2>
            <div className={styles.ratingBox}>
              {[...Array(5)].map((star, i) => {
                const ratingValue = i + 1;

                return (
                  <label key={`star${i}`}>
                    <input
                      type="radio"
                      name="rating"
                      value={ratingValue}
                      onClick={() => setRating(ratingValue)}
                    />
                    <FaStar
                      className={styles.star}
                      color={ratingValue <= rating ? "#ffc107" : "e4e5e9"}
                      size={30}
                    />
                  </label>);
              })}
              <button
                className={styles.sendButton}
                onClick={() => { uploadData(rating, selectedExperience.id) }}
                type="button">
                <i className="las la-check-circle" />
                Envíar
              </button>
            </div>
            <a
              className={styles.resena}
              onClick={() => setResenaPopup(true) }
            >Ver Reseñas
            </a>
            {resenaPopup && <PopupResena trigger={resenaPopup} setTrigger={setResenaPopup} />}
            {saveSuccesfully &&
              <div className={styles.message}>
                <p>La reseña fue agregada con éxito!</p>
              </div>}
          </div>
          <Characteristic />
        </>
      }
    </section>

  )
}

export default Detail;