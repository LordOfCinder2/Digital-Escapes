import { Navigate, useLocation, useNavigate } from "react-router-dom";
import AuthForm from "../../components/AuthForm/component"
import AuthRepository from "../../repositories/auth.repository";
import { useContext, useEffect, useState } from "react";
import { AuthContext } from "../../context/auth/AuthContext";
import Modal from "../../components/UI/Modal/component";
import { decodeJWT } from "../../utils/security/decodeJWT";

const Auth = () => {

  const [message, setMessage] = useState({ message: null, status: null });
  const [showLoadModal, setShowLoadModal] = useState(false);
  const { userData, updateUserData } = useContext(AuthContext);
  const { isAuthenticated } = userData;

  const location = useLocation()
  const navigate = useNavigate();

  const searchParams = new URLSearchParams(location.search);
  const bookingParam = searchParams.get('redirect');

  useEffect(() => {
    setMessage({
      message: null,
      status: null
    })
  }, [])



  const handleOnSubmit = (data) => {
    setMessage({ message: null, status: null })
    setShowLoadModal(true)
    const login = async () => {
      try {
        const response = await AuthRepository.login(data);
        if (response) {
          localStorage.setItem('token', response.token);
          updateUserData({
            user: decodeJWT(response.token),
            isAuthenticated: true
          })

          if(bookingParam){
            navigate("/booking")
          }else{
            navigate("/home")
          }
          
          setShowLoadModal(false)
        }
      } catch (error) {
        setShowLoadModal(false)
        setMessage({
          message: "Las credenciales son incorrectas, por favor verifica e intenta nuevamente!",
          status: "error"
        })
      }
    };

    const register = async () => {
      try {
        const response = await AuthRepository.signUp(data);
        setMessage({
          message: response,
          status: "ok"
        })
        navigate("/login")
        setShowLoadModal(false)
      } catch (error) {
        setShowLoadModal(false)
        setMessage({
          message: error.response.data,
          status: "error"
        })
      }
    };

    if (location.pathname === '/login') login();
    if (location.pathname === '/signup') register();
  }

  if (isAuthenticated) {
    return <Navigate to="/home" />
  }

  return (
    <>
      <AuthForm submitData={handleOnSubmit} errorMsg={message} />
      {showLoadModal && <Modal />}
    </>
  )
}

export default Auth;