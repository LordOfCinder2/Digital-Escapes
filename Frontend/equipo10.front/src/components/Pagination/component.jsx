import { useContext } from "react";
import { ExperiencesContext } from "../../context/coreData/experiences.context";
import styles from "./component.module.scss";
import ExperiencesRepository from "../../repositories/experiences.repository";

const Pagination = () => {

  const { experienceData, updateExperienceData } = useContext(ExperiencesContext);

  const { totalPages, currentPage } = experienceData;

  const pageNumbers = Array.from({ length: totalPages }, (_, index) => index + 1);

  const onPageChange = async (page) =>{
    window.scrollTo({
      top: 0,
      behavior: 'smooth'
    });
    try {
      const response = await ExperiencesRepository.getAllPaginated(page - 1);
      updateExperienceData(
        {
          experiences: response.experienceDTOList,
          currentPage: page,
          loading: false
        })
    } catch (error) {
      updateExperienceData({error: error, loading: false})
    }
  };

  return (
    <div className={styles.paginationContainer}>
      <div className={styles.linksContainer}>
        {pageNumbers.map((pageNumber) => (
          <button
            key={pageNumber}
            onClick={() => onPageChange(pageNumber)}
            disabled={pageNumber === currentPage}
            className={styles.pageLinks}
          >
            {pageNumber}
          </button>
        ))}
      </div>
    </div>

  );
};

export default Pagination;
