import React, { useCallback, useState, useEffect } from "react";
import "./App.css";
import { useDropzone } from "react-dropzone";
import axios from "axios";

const UserProfiles = () => {
  const [profiles, setProfiles] = useState([]);

  const getUsers = () =>
    axios.get("http://localhost:8080/api/v1/profile").then(res => {
      console.log(res.data);
      setProfiles(res.data);
    });

  useEffect(() => {
    getUsers();
  }, []);

  return profiles.map((profile, index) => {
    console.log(profile);
    const image = profile.profileImageKey ? (
      <img
        src={`http://localhost:8080/api/v1/profile/${profile.userProfileId}/image/download`}
      />
    ) : null;
    return (
      <div key={index}>
        {image}
        <br />
        <br />
        <h1>{profile.username}</h1>
        <Dropzone key={index} {...profile} />
        <br />
      </div>
    );
  });
};

function Dropzone({ userProfileId }) {
  const onDrop = useCallback(acceptedFiles => {
    const file = acceptedFiles[0];
    const formData = new FormData();
    formData.append("file", file);
    axios.post(
      `http://localhost:8080/api/v1/profile/${userProfileId}/image/upload`,
      formData,
      {
        headers: {
          "Content-Type": "multipart/form-data"
        }
      }
    );
  }, []);

  const { getRootProps, getInputProps, isDragActive } = useDropzone({ onDrop });

  return (
    <div {...getRootProps()}>
      <input {...getInputProps()} />
      {isDragActive ? (
        <p>Drop image here ...</p>
      ) : (
        <p>Drag 'n' drop profile img, or click to select</p>
      )}
    </div>
  );
}

function App() {
  return (
    <div className="App">
      <UserProfiles />
    </div>
  );
}

export default App;
