import { Link } from "react-router-dom";
import styles from "./component.module.scss";
import PropTypes from 'prop-types';


const ExperienceCard = (props) => {

	const { title, image, slug, price, location } = props

	return (
		<article className={styles.experienceCard}>
			<div className={styles.imgCard}>
				<img className={styles.imgToRender} src={image} />
			</div>

			<div className={styles.description}>
				<div className={styles.title}>
					<h2>{title}</h2>
					<div>
						<p>Precio</p>
						<p className={styles.price}>${price}</p>
					</div>
				</div>

				<p className={styles.location}>
					<i className="las la-map-marker-alt"></i>
					{location}
				</p>

				{/* <div>
					<p className={styles.textDescription}>
						{description}
					</p>
				</div> */}
				<Link to={`/detail/${slug}`} className={styles.seeMore}>		
					Ver más
				</Link>
			</div>
		</article>
	)
}

ExperienceCard.propTypes = {
	title: PropTypes.string,
	image: PropTypes.string,
	slug: PropTypes.string,
	price: PropTypes.number,
	location: PropTypes.string,
	description: PropTypes.string,
}

export default ExperienceCard