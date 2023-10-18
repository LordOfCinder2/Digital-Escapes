import Header from "./Header/component"
import Footer from "./Footer/component"
import styles from "./mainContent.module.scss"
import PropTypes from 'prop-types';

const MainContent = (props) => {

	const {children} = props;

	return (
		<div className={styles.mainApp}>
			<Header />
				{children}
			<Footer />
		</div>
	)
}

MainContent.propTypes = {
	children: PropTypes.node
}



export default MainContent