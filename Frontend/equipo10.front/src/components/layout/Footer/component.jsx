import logo from "../../../assets/images/DE_logo-white.png";
import styles from "./component.module.scss";

const Footer = () => {

	return (
		<footer className={styles.footer}>

			<div className={styles.logoCopyright}>
				<div className={styles.logoContainer}>
					<img className={styles.logoFooter} src={logo} />
				</div>
				<span>
					<i className="las la-copyright" />
					2023 Digital Escapes
				</span>
			</div>

			<div className={styles.socialMedia}>
				<i className="lab la-facebook"></i>
				<i className="lab la-instagram"></i>
				<i className="lab la-twitter"></i>
				<i className="lab la-youtube"></i>
			</div>
		</footer>
	)
}

export default Footer