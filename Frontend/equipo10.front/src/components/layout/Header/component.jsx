import { useContext, useState } from 'react';
import { Link } from 'react-router-dom';
import logoHeader from "../../../assets/images/DE_live-travel.png";
import Avatar from "./children/Avatar/component";
import styles from "./component.module.scss";
import classNames from "classnames";
import { AuthContext } from '../../../context/auth/AuthContext';
import Modal from '../../UI/Modal/component';
import { useNavigate } from "react-router-dom";

const Header = () => {

	const [isMenuOpen, setMenuOpen] = useState(false);
	const [showLoadModal, setShowLoadModal] = useState(false);
	const { userData, updateUserData } = useContext(AuthContext);
  const navigate = useNavigate();
	const { user, isAuthenticated } = userData;

	const toggleMenu = () => {
		setMenuOpen(!isMenuOpen);
	};

	const logout = () => {
		setShowLoadModal(true)
		localStorage.clear();
		updateUserData({
			user: {},
			isAuthenticated: false
		})

		setTimeout(() => {
			setShowLoadModal(false)
		}, "2000");
		navigate("/home")
	}

	return (
		<>
			<header className={styles.nav}>
				<Link to='/'><img src={logoHeader} alt="Digital_Escapes_Logo" className={styles.logoHeader} /></Link>
				<div className={styles.actionsButtons}>
					{isAuthenticated
						?
						<>
							<p className={styles.welcomeMsg}>{`Hola ${user.name}`}</p>
							<Avatar closeSession={logout} />
						</>

						:
						<>
							<Link to='/signup' className={styles['link-nav-login']}>Crear Cuenta</Link>
							<Link to='/login' className={styles['link-nav-signup']}>Iniciar Sesión</Link>
						</>
					}
				</div>
				<div className={styles.mobile}>
					<i className="las la-bars" onClick={toggleMenu}></i>
					<div className={classNames(styles['menuMobile'], isMenuOpen && styles["open"])}>
						<i className="las la-times" onClick={toggleMenu}></i>
						{isAuthenticated
							?
							<>
								<p className={styles.welcomeMsgMobile}>{`Hola ${user.name}`}</p>
								<button className={styles['links']} onClick={logout}>Cerrar sesión</button>
							</>
							:
							<>
								<Link to='/signup' className={styles['links']}>Crear Cuenta</Link>
								<Link to='/login' className={styles['links']}>Iniciar Sesión</Link>
							</>
						}
					</div>
				</div>
			</header>
			{showLoadModal && <Modal />}
		</>
	);
}

export default Header