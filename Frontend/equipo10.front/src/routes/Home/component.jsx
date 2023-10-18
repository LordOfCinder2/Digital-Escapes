import Search from "../../components/Search/component"
import Categories from "../../components/Categories/component"
import Experiences from "../../components/Experiencies/component"
import Pagination from "../../components/Pagination/component"
import { useContext, useEffect } from "react"
import { ExperiencesContext } from "../../context/coreData/experiences.context"
import initExperiencesContext from "../../context/coreData/initExperiencesContext"

const Home = () => {

  const { updateExperienceData} = useContext(ExperiencesContext)
  
  useEffect(() => {
    updateExperienceData(initExperiencesContext)
  }, [updateExperienceData])
  
  
  return (
    <>
      <Search />
      <Categories />
      <Experiences />
      <Pagination />
    </>
  )
}

export default Home