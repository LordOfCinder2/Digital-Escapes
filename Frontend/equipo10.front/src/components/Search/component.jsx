import styles from "./component.module.scss"

const Search = () => {

    return(
        <section className={styles.searchSection}>
            <h1 className={styles.sectionTitle}>Encuentra tu próxima experiencia</h1>
            <div className={styles.searchBar}>
                <div className={styles.destinationSection}>
                    <i className="las la-bed"></i>
                    <span className={styles.spanStyle}>Destino</span>
                    <input type="text" placeholder="Selecciona tu destino ..." className={styles.searchField}/>
                </div>
                
                <div className={styles.datesSection}>

                    <div className={styles.checkIn}>
                        <i className="las la-calendar"></i>
                        <span className={styles.spanStyleDates}>Check In</span>
                        <input type="date" className={styles.datesFields}></input>
                    </div>

                    <div className={styles.checkOut}>
                        <i className="las la-calendar"></i>
                        <span className={styles.spanStyleDates}>Check Out</span>
                        <input type="date" className={styles.datesFields}></input>
                    </div>
                </div>
            </div>

            <div className={styles.searchButtons}>
                <button className={styles.buttonCoupon}>+ Añadir cupón</button>
                <button className={styles.searchButton}>Buscar</button>
            </div>

            

        </section>
    )

}

export default Search;