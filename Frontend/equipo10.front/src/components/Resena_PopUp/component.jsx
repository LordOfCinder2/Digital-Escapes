import { useContext, useEffect, useRef, useState } from 'react';
import { ExperiencesContext } from '../../context/coreData/experiences.context';
import { Rate } from 'antd';
import ScoreRepository from '../../repositories/score.repository';
import PropTypes from 'prop-types';
import styles from "./component.module.scss";

const ResenaPopUp = (props) => {

    const { experienceData } = useContext(ExperiencesContext);
    const { selectedExperience } = experienceData;
    const [ review, setReview ] = useState([]);
    const [ average, setAverage] = useState([]);

    const apiCalled = useRef(false);
    const token = localStorage.getItem('token');

    useEffect(() => {
        if(!apiCalled.current){
            const fetchData = async () => {
                try{
                    const response = await ScoreRepository.getScoresById(selectedExperience.id);
                    setReview(response);
                    const response2 = await ScoreRepository.getAverage(selectedExperience.id, token);
                    setAverage(response2);
                } catch (error){
                    console.log(error)
                }
            };
            fetchData();
            apiCalled.current = true;
        }
    },[selectedExperience.id, token])

    return (props.trigger) ? (
        <div className={styles.popup}>
            <div className={styles['popup-inner']}>
                <button
                className={styles['close-btn']}
                onClick={() => props.setTrigger(false)}
                >
                    <i className="las la-times"></i>
                </button>
                <h1 className={styles.title}>Reseñas</h1>
                <div className={styles.promedio}>
                    <Rate disabled value={average} />
                    <p>{average} de 5</p>
                </div>
                <h3>Calificaciones de usuarios: </h3>
                {review && review.map((element, index) =>(
                    <div key={index} className={styles.calificaciones}>
                        {element} <Rate disabled value={element} />
                    </div>
                ))}
            </div>
        </div>
    ) : "";

}

ResenaPopUp.propTypes = {
    trigger: PropTypes.bool,
    setTrigger: PropTypes.func
}

export default ResenaPopUp;