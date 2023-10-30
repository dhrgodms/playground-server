import React, {useEffect, useState} from 'react';
import {Flex, SimpleGrid, Skeleton} from '@chakra-ui/react';
import axios from "axios";
import MainTemplate from "../../Templates/MainTemplate";
import {PostCard} from "../../Components/PostCard";

const ToonPostsList = () => {
    const [toonPosts, setToonPosts] = useState([{id:1,postListTile:'꾸잉',postNo:1}]);
    const [isLoaded, setIsLoaded] = useState(false);
    useEffect(() => {
        (toonPosts?.length<2)&&axios.get('http://localhost:8080/api/post/tag/2').then(response => {
            setToonPosts(response.data);
            setIsLoaded(true);
        }).catch(error=>console.log(error));
        console.log(toonPosts);
    },[toonPosts]);
    return (
        <MainTemplate pageTitle={'끄적..만화 모음'} titleQuery={'만화'}>
            <Flex height={'100vh'} style={{ flexDirection:'column' }}>
                    <Skeleton isLoaded={isLoaded} fadeDuration={1}>
                <SimpleGrid
                    spacing={4}
                    templateColumns="repeat(auto-fill, minmax(200px, 1fr))"
                >
                    {toonPosts.map(item => (
                        <PostCard key={item.id} post={item} />
                    ))}
                </SimpleGrid>
                    </Skeleton>
            </Flex>
        </MainTemplate>
    );
};

export default ToonPostsList;
