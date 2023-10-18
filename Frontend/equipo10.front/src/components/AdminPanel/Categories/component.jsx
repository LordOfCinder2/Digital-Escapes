import { Link } from "react-router-dom";
import styles from "./component.module.scss";
import AdminCustomTable from "../children/customTable/component";
import { useEffect, useRef, useState } from "react";
import Spinner from "../../Spinner/component";
import CategoriesRepository from "../../../repositories/categories.repository";

const MainCategories = () =>{

  const [categories, setCategories] = useState([]);
  const [loading, setLoading] = useState(true);
  const apiCalled = useRef(false);

  useEffect(() => {
		if (!apiCalled.current) {
      const fetchData = async () => {
        try {
          const response = await CategoriesRepository.getAllCategories();
          setCategories(response)
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
      <h1>Administrar categorias</h1>
      <Link to={"addCategories"}>Agregar categoria</Link>
      <div>
        {loading 
          ? <Spinner />
          : <AdminCustomTable data={categories} />
        }
      </div>
    </section>
  )
}

export default MainCategories;