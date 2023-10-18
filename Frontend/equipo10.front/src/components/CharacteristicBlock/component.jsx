import { useContext } from 'react';
import CaracteristicaItem from "./children/Characteristic/component";
import styles from "./component.module.scss";
import { ExperiencesContext } from '../../context/coreData/experiences.context';

const Characteristic = () => {

  const { experienceData } = useContext(ExperiencesContext)
  const { selectedExperience } = experienceData
  const { characteristicSet } = selectedExperience
  
  return (
    <div className={styles.divContainer}>
      <button className={styles.btn}>
        ¿Qué ofrece este lugar?
      </button>
      <div className={styles.divCharacteristic}>
        <ul>
          { characteristicSet && characteristicSet.map((element) => (
            <CaracteristicaItem key={element.id} caracteristica={element} />
          ))}
        </ul>
      </div>
    </div>
  );
};


export default Characteristic;
