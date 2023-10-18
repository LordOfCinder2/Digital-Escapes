import { LeftCircleFilled, RightCircleFilled} from "@ant-design/icons";
import PropTypes from 'prop-types';

const SlickArrow = ({onClick, arrow}) => {
  return arrow === 'next' ? (
    <RightCircleFilled onClick={onClick} className="slick-next slick-arrow" />
  ) : (
    <LeftCircleFilled onClick={onClick} className="slick-prev slick-arrow" />
  )
}

SlickArrow.propTypes = {
  arrow: PropTypes.string,
  onClick: PropTypes.func
}

export default SlickArrow;