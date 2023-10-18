import Spinner from "../../Spinner/component";
import styles from "./component.module.scss";

const Modal = () =>{

  return(
    <div className={styles.modal}>
      <Spinner />
    </div>
  )
}

export default Modal;