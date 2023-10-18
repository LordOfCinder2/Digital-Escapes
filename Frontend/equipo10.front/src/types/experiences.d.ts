export type TCategory = {
  id: number
}

export type TAddExperience = {
  title: string;
  experience_slug: string;
  category: TCategory;
  description: string;
  image: string;
  location: string;
  price: number;
}

export type TAddBooking = {
  experienceId: number;
  departureDate: string;
  returnDate: string
}