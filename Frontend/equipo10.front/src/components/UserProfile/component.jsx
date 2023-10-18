import { useContext } from "react";
import userAvatar from "../../assets/images/user.png";
import styles from "./component.module.scss";
import { AuthContext } from '../../context/auth/AuthContext';

const UserProfile = () => {

	const { userData } = useContext(AuthContext);
	const { user } = userData;

	return (
		<div className={styles.userData}>
			<div className={styles.avatarImg}>
				<img src={userAvatar} alt="User Photo" className={styles.avatar}></img>
			</div>
			<div className={styles.userInfo}>
				<h1 className={styles.infoPersonal}>Información Personal</h1>
				<div>
					<div className={styles.contenedor}>
						<i className="las la-user" />
						<h2>Nombre Completo</h2>
					</div>
					<span className={styles.data}>{user.name}</span>

					<div className={styles.contenedor}>
						<i className="las la-phone" />
						<h2>Teléfono</h2>
					</div>
					<span className={styles.data}>{user.phone}</span>

					<div className={styles.contenedor}>
						<i className="las la-at" />
						<h2>Correo Eletrónico</h2>
					</div>
					<span className={styles.data}>{user.sub}</span>
				</div>
			</div>

		</div>

	)
}

export default UserProfile;