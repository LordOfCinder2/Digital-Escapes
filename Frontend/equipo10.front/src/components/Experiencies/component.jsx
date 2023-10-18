import { useContext, useEffect, useRef, useState } from 'react'
import ExperienceCard from './children/ExperiencesCard/component'
import styles from './component.module.scss'
import Spinner from '../Spinner/component'
import ExperiencesRepository from '../../repositories/experiences.repository'
import { ExperiencesContext } from '../../context/coreData/experiences.context'

const Experiences = () => {

	const { experienceData, updateExperienceData} = useContext(ExperiencesContext)
	const [loading, setLoading] = useState(true);
	const { experiences, currentPage, error, selectedCategory} = experienceData

	const apiCalled = useRef(false);

	useEffect(() => {
		if (!apiCalled.current) {
      const fetchData = async () => {
        try {
          const response = await ExperiencesRepository.getAllPaginated(currentPage - 1);
          updateExperienceData({
            experiences: response.experienceDTOList,
            totalExperiences: response.totalElements,
            totalPages: response.totalPages,
            currentPage: currentPage,
						selectedExperience: {}
          });
          setLoading(false);
        } catch (error) {
          updateExperienceData({
            experiences: [],
            error: error,
          });
          setLoading(false);
        }
      };
      fetchData();
      apiCalled.current = true;
    }
	}, [currentPage, updateExperienceData])

	return (
		<section className={styles.experiencesSection} >
			<h2 className={styles.sectionTitle}>
				{selectedCategory.title ? selectedCategory.title : 'Recomendaciones'}
			</h2>
			{loading && <Spinner />}
			{error && <p>{error.message}</p>}
			<div className={styles.experiencesList}>
				{experiences &&
					experiences.map((element, index) => (
						<ExperienceCard
							key={`experience_${index}`}
							title={element.name}
							image={element.imgUrl}
							slug={element.experienceSlug}
							price={element.price}
							location={element.location}
							description={element.description}
						/>
					))}
			</div>
		</section>
	)
}

export default Experiences
