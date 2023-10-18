import { useContext, useEffect, useRef, useState } from "react";
import CategoriesCard from "./children/CategoriesCard/component";
import styles from "./component.module.scss"
import { ExperiencesContext } from "../../context/coreData/experiences.context";
import CategoriesRepository from "../../repositories/categories.repository";
import { isBrowser } from "react-device-detect";
import { Carousel } from "antd";
import Spinner from "../Spinner/component";
import SlickArrow from "./children/SlickArrow/component";
import ExperiencesRepository from "../../repositories/experiences.repository";

const Categories = () =>{

	const { experienceData, updateExperienceData} = useContext(ExperiencesContext)
	const [loading, setLoading] = useState(true);
	const { categories, currentPage, error } = experienceData

	const apiCalled = useRef(false);

	useEffect(() => {
		if (!apiCalled.current) {
      const fetchData = async () => {
        try {
          const response = await CategoriesRepository.getAllCategories();
          updateExperienceData({
						categories: response
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
	}, [updateExperienceData])

	const handleSelectedCategory = (id, title) =>{

		updateExperienceData({
			selectedCategory: {
				id: id,
				title: title
			}
		});	
	
		const fetchData = async () => {
			try {
				// const token = localStorage.getItem('token');
				const response = await ExperiencesRepository.getByCategory(id);
				updateExperienceData({
					experiences: response.experienceDTOList,
					totalExperiences: response.totalElements,
					totalPages: response.totalPages,
					currentPage: currentPage,
					selectedExperience: {}
				});	
			} catch (error) {
				updateExperienceData({
					experiences: [],
					error: error,
				});
			}
		};
		fetchData();
	}


	const settingsSlide = {
		arrows: true,
		dots: false,
		slidesToScroll: isBrowser ? 3 : 1,
		slidesToShow: isBrowser ? 3 : 1, 
		prevArrow: <SlickArrow arrow={'prev'}/>,
		nextArrow: <SlickArrow arrow={'next'}/>
	}

  return(
    <section className={styles.categoriesSection} >
      <h2 className={styles.sectionTitle}>Categorias</h2>
			{loading && <Spinner />}
			{error && <p>{error.message}</p>}
      <div className={styles.categoriesList}>
			<Carousel {...settingsSlide} >
			{categories &&
				categories.map((element) => (
          <CategoriesCard 
            key={`category_${element.name}`}
            title={element.name}
            id={element.id}
						img={element.imgUrl}
						onClick = {handleSelectedCategory}
          />
				))}
			</Carousel>
      </div>
    </section>
  )
}


export default Categories;