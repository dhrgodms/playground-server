import React, {useEffect, useState} from 'react';
import {Flex, SimpleGrid} from '@chakra-ui/react';
import axios from "axios";
import {PostCard} from "../../Components/PostCard";
import MainTemplate from "../../Templates/MainTemplate";

const AllPostsList = () => {
    const [writePosts, setWritePosts] = useState([{id:1,postListTile:'꾸잉',postNo:1}]);

    useEffect(() => {
        (writePosts?.length<2)&&axios.get('http://localhost:8080/api/post/all').then(response => setWritePosts(response.data)).catch(error=>console.log(error));
        console.log(writePosts);
    },[writePosts]);
    return (
        <MainTemplate pageTitle={'모두 모아보기'} titleQuery={'모두'}>
            <Flex height={'100vh'} style={{ flexDirection: 'column' }}>
                <SimpleGrid
                    spacing={4}
                    templateColumns="repeat(auto-fill, minmax(200px, 1fr))"
                >
                    {writePosts.map(item => (
                        (item.id>1)?(<PostCard key={item.id} post={item}/>):null
                    ))}
                </SimpleGrid>
            </Flex>
        </MainTemplate>
    );
};

export default AllPostsList;