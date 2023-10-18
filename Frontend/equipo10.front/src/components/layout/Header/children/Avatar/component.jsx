import { generateRandomHexColor } from "../../../../../utils/UI/randomColors";
import { Button, Menu} from "antd";
import { Link } from "react-router-dom";
import styles from "./component.module.scss";
import PropTypes from 'prop-types';
import { useContext } from "react";
import { AuthContext } from "../../../../../context/auth/AuthContext";
import { slice } from "ramda"


const Avatar = ({ closeSession }) => {

  const { userData } = useContext(AuthContext);

  const { user } = userData;

  const items = [
    {
      key: '0',
      label: (
        <Link to='adminPanel' className={styles.links}>Panel de administración</Link>
      ),
    },
    {
      key: '1',
      label: (
        <Link to='/userdetail' className={styles['link-nav-login']}>Mi perfil</Link>
      ),
    },
    {
      key: '2',
      label: (
        <p className={styles.links} onClick={closeSession}>Cerrar sesión</p>
      ),
    },
  ];

  
  const initials = user.name.slice(0, 1)
  const authItems = user.role === 'ADMIN' ? items : slice(1, Infinity , items )
  const randomColor = generateRandomHexColor()

  const menu = (
    <>
      {authItems.map(item => (
        <Menu.Item key={item.key}>{item.label}</Menu.Item>
      ))}
    </>
  );

  return (
    <Menu mode="horizontal" selectable={false} className={styles.menu}>
      <Menu.SubMenu key="avatar" title={
        <Button style={{ backgroundColor: randomColor }} className={styles.avatar}>
          <span>{initials}</span>
        </Button>
      }>
        {menu}
      </Menu.SubMenu>
    </Menu>
  );
};

Avatar.propTypes = {
  name: PropTypes.string,
  closeSession: PropTypes.func
}

export default Avatar;