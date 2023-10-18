import PropTypes from 'prop-types';

const CaracteristicaItem = ({ caracteristica }) => {
    const { icon, name } = caracteristica
  
    return (
      <li>
        {icon && <img src={icon} alt={name} />}
        <span>{name}</span>
      </li>
    );
  };
  
  CaracteristicaItem.propTypes = {
    icon: PropTypes.string,
    name: PropTypes.string
  }
  
  export default CaracteristicaItem;