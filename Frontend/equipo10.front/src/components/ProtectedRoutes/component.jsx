import { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { AuthContext } from '../../context/auth/AuthContext';
import PropTypes from 'prop-types';

const AuthGuard = ({children}) => {

  const { userData } = useContext(AuthContext);

  const { user } = userData;

  if (user.role !== 'ADMIN') {
    return <Navigate to="/login" />;
  }

  return children;
};

AuthGuard.propTypes = {
  children: PropTypes.node
}

export default AuthGuard;