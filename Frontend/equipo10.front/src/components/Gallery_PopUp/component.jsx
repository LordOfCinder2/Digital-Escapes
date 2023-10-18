import { useContext } from 'react';
import { ExperiencesContext } from '../../context/coreData/experiences.context'
import styles from "./component.module.scss";
import PropTypes from 'prop-types';

const GalleryPopUp = (props) => {
  const { experienceData } = useContext(ExperiencesContext);
  const { selectedExperience } = experienceData;

  return (props.trigger) ? (
    <div className={styles.popup}>
      <div className={styles['popup-inner']}>
        <section>
          <img src={selectedExperience.imgUrl} className={styles.imgMain}></img>
          <h1 className={styles.title}>1/15</h1>
          <div className={styles.grid1x4}>
            <img src={selectedExperience.imgUrl} className={styles.imgGrid}></img>
            <img src={selectedExperience.imgUrl} className={styles.imgGrid}></img>
            <img src={selectedExperience.imgUrl} className={styles.imgGrid}></img>
            <img src={selectedExperience.imgUrl} className={styles.imgGrid}></img>
          </div>
        </section>
        <button className={styles['next-btn']}>
          <i className="las la-angle-right"></i>
        </button>
        <button
          className={styles['close-btn']}
          onClick={() => props.setTrigger(false)}
        >
          <i className="las la-times"></i>
        </button>
      </div>

    </div>
  ) : "";
}

GalleryPopUp.propTypes = {
	trigger: PropTypes.bool,
  setTrigger: PropTypes.func
}

export default GalleryPopUp;