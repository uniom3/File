import React, { useEffect, useState } from 'react';
import { getAllSubDirectoriesAndFiles } from '../api/api';
import DirectoryItem from './DirectoryItem';

const DirectoryList = () => {
    const [directories, setDirectories] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchDirectories = async () => {
            try {
                const data = await getAllSubDirectoriesAndFiles();
                setDirectories(data);
            } catch (err) {
                console.error("Error fetching directories:", err);
                setError('Error loading directories');
            } finally {
                setLoading(false);
            }
        };

        fetchDirectories();
    }, []);

    if (loading) return <p>Loading...</p>;
    if (error) return <p>{error}</p>;

    return (
        <div>
            <h1>Directory List</h1>
            {directories.length > 0 ? (
                directories.map(directory => (
                    <DirectoryItem key={directory.id} directory={directory} />
                ))
            ) : (
                <p>No directory found.</p>
            )}
        </div>
    );
};

export default DirectoryList;
