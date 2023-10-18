import { Link } from "react-router-dom";
import styles from "./component.module.scss";
import AdminCustomTable from "../children/customTable/component";
import { useContext, useEffect, useRef, useState } from "react";
import ExperiencesRepository from "../../../repositories/experiences.repository";
import Spinner from "../../Spinner/component";
import { AdminContext } from "../../../context/admin/AdminContext";

const MainExperiences = () =>{

  // const [experiences, setExperiences] = useState([]);

  const { adminData, updateAdminData } = useContext(AdminContext)

  const [loading, setLoading] = useState(true);
  const apiCalled = useRef(false);

  useEffect(() => {
		if (!apiCalled.current) {
      const fetchData = async () => {
        try {
          const token = localStorage.getItem('token');
          const response = await ExperiencesRepository.search(token);
          // setExperiences(response)
          updateAdminData({
            experiences: response
          })
          setLoading(false);
        } catch (error) {
          setLoading(false);
        }
      };
      fetchData();
      apiCalled.current = true;
    }
	}, [updateAdminData])

  return(
    <section className={styles.mainSection} >
      <h1>Administrar experiencias</h1>
      <Link to={"addExperiences"}>Agregar experiencia</Link>
      <div>
        {loading 
          ? <Spinner />
          : <AdminCustomTable data={adminData.experiences} />
        }
      </div>
    </section>
  )
}

export default MainExperiences;