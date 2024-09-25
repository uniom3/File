import React from 'react';

const FileItem = ({ file }) => {
    return (
        <div className="file-item">
            <p>{file.name} ({file.size} bytes) - Tipo: {file.type}</p>
        </div>
    );
};

export default FileItem;
