import { useContext } from 'react';
import styles from "./component.module.scss";
import classNames from "classnames";
import PropTypes from 'prop-types';
import { ExperiencesContext } from '../../../../context/coreData/experiences.context';

const CategoriesCard = (props) => {

  const {id, title, img, onClick } = props
  const { experienceData } = useContext(ExperiencesContext)
  const { selectedCategory } = experienceData

  const handleClick = () =>{
    onClick(id, title);
  }

  return (

    <article className={classNames(styles.experienceCard, selectedCategory.id === id ? styles.cardSelected : null)} onClick={handleClick}>
      <div className={styles.imgCard}>
        <img className={styles.imgToRender} src={img} />
      </div>

      <div className={styles.description}>
        <p className={styles.title}>{title}</p>
      </div>
    </article>
  )
}

CategoriesCard.propTypes = {
  id: PropTypes.number,
  title: PropTypes.string,
  quantity: PropTypes.number,
  img: PropTypes.string,
  onClick: PropTypes.func
}

export default CategoriesCard;