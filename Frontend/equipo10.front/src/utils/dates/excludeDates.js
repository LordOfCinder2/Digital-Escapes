import { parseISO } from "date-fns";

export const excludeDates = (dates) =>{
  if(!dates) return;
  return dates.map(({departureDate, returnDate})=>(
    {
      start: parseISO(departureDate),
      end: parseISO(returnDate)
    }
  ))
}