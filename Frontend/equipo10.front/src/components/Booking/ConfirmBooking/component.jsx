import { useContext, useEffect, useRef, useState } from "react"
import { ExperiencesContext } from "../../../context/coreData/experiences.context"
import DatePicker from "react-datepicker";
import styles from "./component.module.scss";
import "react-datepicker/dist/react-datepicker.css";
import 'react-datepicker/dist/react-datepicker-cssmodules.css';
import { Button, Modal, message } from "antd"
import ExperiencesRepository from "../../../repositories/experiences.repository";
import { excludeDates } from "../../../utils/dates/excludeDates";
import { useNavigate } from "react-router-dom";
import { format } from 'date-fns';

const ConfirmBookingForm = () => {

  const { experienceData } = useContext(ExperiencesContext);
  const { bookingData, selectedExperience } = experienceData;
  const [startDate, setStartDate] = useState(new Date(bookingData.departureDate));
  const [endDate, setEndDate] = useState(new Date(bookingData.returnDate));
  const [bookedDates, setBookedDates] = useState([])
  const [open, setOpen] = useState(false);
  const [modalText, setModalText] = useState('Realizando reserva, porfavor espere...');
  const [confirmLoading, setConfirmLoading] = useState(false);
  const [messageApi, contextHolder] = message.useMessage()

  const navigate = useNavigate();

  const apiCalled = useRef(false);

  useEffect(() => {
    if (!apiCalled.current) {
      const fetchData = async () => {
        try {
          const response = await ExperiencesRepository.getDatesByExp(bookingData.experienceId)
          setBookedDates(response)
        } catch (error) {
          console.log(error)
        }
      };
      fetchData();
      apiCalled.current = true;
    }
  }, [bookingData.experienceId])

  const handleBooking = () => {
    setOpen(true)
    setConfirmLoading(true);

    const bookingParsedToSend = {
      ...bookingData,
      departureDate: format(bookingData.departureDate, 'yyyy-MM-dd'),
      returnDate: format(bookingData.returnDate, 'yyyy-MM-dd'),
    }

    const fetchData = async () => {
      try {
        const token = localStorage.getItem('token');
        const response = await ExperiencesRepository.addBooking(bookingParsedToSend, token);
        setModalText(`La reserva se realizo con exito, el codigo de tu reserva es ${response}, te hemos enviado un correo electronico con toda la informacion.`)
        setConfirmLoading(false);

      } catch (error) {
        console.log(error)
        setOpen(false);
        setConfirmLoading(false);
        messageApi.open({
          type: 'error',
          content: 'Hubo un error, intentelo nuevamente',
        });
      }
    };
    fetchData();
  }

  return (
    <>
      {contextHolder}
      <div className={styles.bookingContainer}>
        <h1>Confirma tu reserva</h1>
        <section className={styles.bookingForm}>
          <div>
            <label>Destino:</label>
            <h2>{selectedExperience.name}</h2>
          </div>

          <div>
            <label>Selecciona la fecha de inicio:</label>
            <DatePicker
              selected={startDate}
              onChange={(date) => setStartDate(date)}
              excludeDateIntervals={excludeDates(bookedDates)}
              selectsStart
              startDate={startDate}
              endDate={endDate}
              minDate={startDate}
              className={styles.calendarInput}
            />
          </div>

          <div>
            <label>Selecciona la fecha final:</label>
            <DatePicker
              selected={endDate}
              onChange={(date) => setEndDate(date)}
              excludeDateIntervals={excludeDates(bookedDates)}
              selectsEnd
              startDate={startDate}
              endDate={endDate}
              minDate={startDate}
              className={styles.calendarInput}
            />
          </div>

          <div>
            <label>Precio:</label>
            <span>{selectedExperience.price}</span>
          </div>

          <div className={styles.buttonContainer}>
            <Button
              className={styles.validateButton}
              onClick={handleBooking}
            >
              Reservar
            </Button>

            <Button
              className={styles.cancelButton}
              onClick={() => (navigate(-1))}
            >
              Cancelar
            </Button>
          </div>
        </section>
      </div>
      <Modal
        centered
        open={open}
        onOk={() => (navigate('/home'))}
        closable={false}
        confirmLoading={confirmLoading}
        cancelButtonProps={{
          disabled: true,
        }}
      >
        <p className={styles.modalText}>{modalText}</p>
      </Modal>
    </>

  )
}

export default ConfirmBookingForm