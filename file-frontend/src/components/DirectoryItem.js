import React from 'react';

const DirectoryItem = ({ directory }) => {
    return (
        <div style={{ marginLeft: '20px' }}>
            {directory.name && <h4>{directory.name}</h4>} {}
            {directory.files && directory.files.length > 0 && (
                <ul>
                    {directory.files.map(file => (
                        <li key={file.id} style={{ marginLeft: '20px' }}>
                            - {file.name}
                        </li>
                    ))}
                </ul>
            )}
            {directory.subDirectoryDtos && directory.subDirectoryDtos.length > 0 && (
                <ul>
                    {directory.subDirectoryDtos
                        .filter(subDirectory => subDirectory.id !== null) 
                        .map(subDirectory => (
                            <li key={subDirectory.id}>
                                <DirectoryItem directory={subDirectory} />
                            </li>
                    ))}
                </ul>
            )}
        </div>
    );
};

export default DirectoryItem;
