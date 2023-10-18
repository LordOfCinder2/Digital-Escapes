import styles from "./component.module.scss";
import AdminCustomTable from "../children/customTable/component";
import { useEffect, useRef, useState } from "react";
import Spinner from "../../Spinner/component";
import AuthRepository from "../../../repositories/auth.repository";

const MainUsers = () =>{

  const [users, setUsers] = useState([]);
  const [loading, setLoading] = useState(true);
  const apiCalled = useRef(false);

  useEffect(() => {
		if (!apiCalled.current) {
      const fetchData = async () => {
        try {
          const token = localStorage.getItem('token');
          const response = await AuthRepository.getAllUsers(token);
          setUsers(response)
          setLoading(false);
        } catch (error) {
          setLoading(false);
        }
      };
      fetchData();
      apiCalled.current = true;
    }
	}, [])

  return(
    <section className={styles.mainSection} >
      <h1>Administrar usuarios</h1>
      {/* <Link to={"addExperiences"}>Agregar experiencia</Link> */}
      <div>
        {loading 
          ? <Spinner />
          : <AdminCustomTable data={users} />
        }
      </div>
    </section>
  )
}

export default MainUsers;