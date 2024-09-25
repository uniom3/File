import axios from 'axios';

export const getAllSubDirectoriesAndFiles = async () => {
    const response = await axios.get('http://localhost:8080/api/filesystem/subdirectories-files'); 
    return response.data;
};
