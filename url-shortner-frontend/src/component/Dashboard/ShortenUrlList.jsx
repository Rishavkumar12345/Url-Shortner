import React from 'react'
import ShortenItem from './ShortenItem'

const ShortenUrlList = ({data}) => {
  // Add null checking to prevent map error
  if (!data || !Array.isArray(data)) {
    return (
      <div className='my-6 space-y-4'>
        <p>No data available</p>
      </div>
    )
  }

  return (
    <div className='my-6 space-y-4'>
        {data.map((item) => (
            <ShortenItem key={item.id} {...item} />
        ))}
    </div>
  )
}

export default ShortenUrlList