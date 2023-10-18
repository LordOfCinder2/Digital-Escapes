import PropTypes from 'prop-types';
import { Link } from 'react-router-dom';
import styles from "./component.module.scss"

const AdminActionCard = ({ tag, url, icon }) => {

  return (
    <Link to={url} className={styles.links}>
      <h2>{tag}</h2>
      <i className={icon}></i>
    </Link>
  )
}

AdminActionCard.propTypes = {
  tag: PropTypes.string,
  url: PropTypes.string,
  icon: PropTypes.string
}

export default AdminActionCard;