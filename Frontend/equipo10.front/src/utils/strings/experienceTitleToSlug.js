import { trim } from "ramda"

const experienceTitleToSlug = (title) =>{
  const titleTrimed = trim(title).toLowerCase()
  return titleTrimed.replace(/\s/g, '-')
}

export default experienceTitleToSlug;