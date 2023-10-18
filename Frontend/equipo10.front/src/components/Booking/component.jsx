import { useContext, useEffect, useRef, useState } from "react"
import { ExperiencesContext } from "../../context/coreData/experiences.context"

import DatePicker from "react-datepicker";
import styles from "./component.module.scss";
import "react-datepicker/dist/react-datepicker.css";
import 'react-datepicker/dist/react-datepicker-cssmodules.css';
import { Button } from "antd"
import ExperiencesRepository from "../../repositories/experiences.repository";
import { excludeDates } from "../../utils/dates/excludeDates";
import { AuthContext } from "../../context/auth/AuthContext";
import { useNavigate } from "react-router-dom";

const BookingForm = () => {

  const { experienceData, updateExperienceData } = useContext(ExperiencesContext);
  const { selectedExperience } = experienceData;
  const { userData } = useContext(AuthContext)
  const [startDate, setStartDate] = useState(new Date());
  const [endDate, setEndDate] = useState(new Date());
  const [bookedDates, setBookedDates] = useState([])
  const navigate = useNavigate();

  const apiCalled = useRef(false);

	useEffect(() => {
		if (!apiCalled.current && selectedExperience.id) {
      // const token = localStorage.getItem('token');
      const fetchData = async () => {
        try {
          const response = await ExperiencesRepository.getDatesByExp(selectedExperience.id)
          setBookedDates(response)
        } catch (error) {
          console.log(error)
        }
      };
      fetchData();
      apiCalled.current = true;
    }
	}, [selectedExperience])

  const handleBooking = () =>{

    updateExperienceData({
      bookingData: {
        experienceId: selectedExperience.id,
        departureDate: startDate,
        returnDate: endDate
      }
    })

    if(userData.isAuthenticated){
      navigate('/booking')
    }else{
      navigate('/login?redirect=booking')
    }
  }

  return (
    <section className={styles.bookingForm}>
      <div>
        <label>Selecciona la fecha de inicio</label>
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
        <label>Selecciona la fecha final</label>
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

      <Button 
        className={styles.validateButton}
        onClick={handleBooking}
      >
        Reservar
      </Button>
    </section>
  )
}

export default BookingForm